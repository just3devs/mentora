import { ApiError } from './api';
import type { GeneralErrorResponse } from "../types/api.ts";

export interface ErrorInfo {
    message: string;
    isRetryable: boolean;
}

const HTTP_STATUS = {
    BAD_REQUEST: 400,
    UNAUTHORIZED: 401,
    FORBIDDEN: 403,
    PAYLOAD_TOO_LARGE: 413,
    UNSUPPORTED_MEDIA_TYPE: 415,
    TOO_MANY_REQUESTS: 429,
    INTERNAL_SERVER_ERROR: 500,
    BAD_GATEWAY: 502,
    SERVICE_UNAVAILABLE: 503,
    GATEWAY_TIMEOUT: 504
} as const;

const RETRYABLE_STATUS_CODES: readonly number[] = [
    HTTP_STATUS.TOO_MANY_REQUESTS,
    HTTP_STATUS.INTERNAL_SERVER_ERROR,
    HTTP_STATUS.BAD_GATEWAY,
    HTTP_STATUS.SERVICE_UNAVAILABLE,
    HTTP_STATUS.GATEWAY_TIMEOUT
];

const RETRYABLE_ERROR_CODES: readonly string[] = ['OPENAI_API_ERROR', 'PROCESSING_ERROR'];

const NETWORK_ERROR_KEYWORDS = ['fetch', 'network', 'Failed to fetch'];

export const parseApiError = (error: unknown): ErrorInfo => {
    if (error instanceof ApiError) {
        return handleApiError(error);
    }

    if (error instanceof Error) {
        return handleGenericError(error);
    }

    return {
        message: 'An unexpected error occurred',
        isRetryable: true
    };
};

const handleApiError = (error: ApiError): ErrorInfo => {
    if (error.errorData && 'errorMessages' in error.errorData) {
        const generalError = error.errorData as GeneralErrorResponse;
        const message = extractErrorMessages(generalError);

        return {
            message: message || 'An error occurred',
            isRetryable: isRetryableError(error.status, generalError.errorCode)
        };
    }

    return {
        message: getStatusMessage(error.status),
        isRetryable: isRetryableStatus(error.status)
    };
};

const extractErrorMessages = (generalError: GeneralErrorResponse): string => {
    if (!generalError.errorMessages?.length) {
        return '';
    }

    return generalError.errorMessages
        .map((err) => err.message)
        .filter(Boolean)
        .join('; ');
};

const handleGenericError = (error: Error): ErrorInfo => {
    const isNetworkError = NETWORK_ERROR_KEYWORDS.some(keyword =>
        error.message.toLowerCase().includes(keyword.toLowerCase())
    );

    return {
        message: isNetworkError
            ? 'Network error. Please check your connection.'
            : error.message,
        isRetryable: isNetworkError
    };
};

const STATUS_MESSAGES: Record<number, string> = {
    [HTTP_STATUS.BAD_REQUEST]: 'Invalid request. Please check your input and try again.',
    [HTTP_STATUS.UNAUTHORIZED]: 'Authentication required. Please log in and try again.',
    [HTTP_STATUS.FORBIDDEN]: 'Access denied. You do not have permission to perform this action.',
    [HTTP_STATUS.PAYLOAD_TOO_LARGE]: 'File size too large. Please select smaller files and try again.',
    [HTTP_STATUS.UNSUPPORTED_MEDIA_TYPE]: 'Unsupported file type. Please check file formats and try again.',
    [HTTP_STATUS.TOO_MANY_REQUESTS]: 'Too many requests. Please wait a moment and try again.',
    [HTTP_STATUS.INTERNAL_SERVER_ERROR]: 'Server error. Please try again later.',
    [HTTP_STATUS.BAD_GATEWAY]: 'Service temporarily unavailable. Please try again later.',
    [HTTP_STATUS.SERVICE_UNAVAILABLE]: 'Service temporarily unavailable. Please try again later.',
    [HTTP_STATUS.GATEWAY_TIMEOUT]: 'Service temporarily unavailable. Please try again later.'
};

const getStatusMessage = (status: number): string => {
    return STATUS_MESSAGES[status] || `Request failed with status ${status}. Please try again.`;
};

const isRetryableStatus = (status: number): boolean => {
    return RETRYABLE_STATUS_CODES.includes(status);
};

const isRetryableError = (status: number, errorCode?: string): boolean => {
    if (isRetryableStatus(status)) {
        return true;
    }

    if (errorCode) {
        return RETRYABLE_ERROR_CODES.includes(errorCode);
    }

    return false;
};