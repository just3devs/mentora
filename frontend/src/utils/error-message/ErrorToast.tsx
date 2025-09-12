import React from 'react';

interface ErrorToastProps {
    error: string;
    onClose: () => void;
}

const ErrorToast: React.FC<ErrorToastProps> = ({ error, onClose }) => {
    if (!error) return null;
    return (
        <div className="fixed top-4 left-1/2 transform -translate-x-1/2 bg-white border border-gray-200 text-gray-800 px-4 py-3 rounded-lg shadow-lg z-50">
            <div className="flex items-center space-x-2">
                <span className="text-sm">{error}</span>
                <button onClick={onClose} className="text-gray-400 hover:text-gray-600 ml-2">
                    ✕
                </button>
            </div>
        </div>
    );
};

export default ErrorToast;
