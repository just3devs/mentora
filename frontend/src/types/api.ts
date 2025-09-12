export interface GeneralErrorResponse {
    errorCode: string;
    errorMessages: Array<{
        field: string;
        message: string;
    }>;
}