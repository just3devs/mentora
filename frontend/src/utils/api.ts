import type {
    GeneralErrorResponse,
} from "../types/api";

export class ApiError extends Error {
    public status: number;
    public errorData: GeneralErrorResponse;

    constructor(
        message: string,
        status: number,
        errorData: GeneralErrorResponse
    ) {
        super(message);
        this.name = "ApiError";
        this.status = status;
        this.errorData = errorData;
    }
}

const API_BASE_URL = import.meta.env.VITE_API_URL || "/api";

export const logout = async (): Promise<void> => {
    try {
        const response = await fetch(`${API_BASE_URL}/auth/logout`, {
            method: "POST",
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
            },
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
    } catch (error) {
        console.error("Error logging out:", error);
        throw error;
    }
};

