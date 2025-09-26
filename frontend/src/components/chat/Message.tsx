import React, {useState} from "react";
import {Bot, Check, Copy, User} from "lucide-react";
import "./Message.css";

export interface MessageProps {
    id?: number;
    role: "user" | "bot";
    content: string;
}

interface CodePart {
    type: 'code';
    language: string;
    content: string;
    index: number;
}

interface TextPart {
    type: 'text';
    content: string;
}

type ContentPart = CodePart | TextPart;

const Message: React.FC<MessageProps> = ({role, content}) => {
    const isUser = role === "user";
    const [copiedBlocks, setCopiedBlocks] = useState<Set<number>>(new Set());

    const copyToClipboard = async (text: string, blockIndex: number) => {
        try {
            await navigator.clipboard.writeText(text);
            setCopiedBlocks(prev => new Set(prev).add(blockIndex));
            setTimeout(() => {
                setCopiedBlocks(prev => {
                    const newSet = new Set(prev);
                    newSet.delete(blockIndex);
                    return newSet;
                });
            }, 2000);
        } catch (err) {
            console.error('Failed to copy text: ', err);
        }
    };

    const parseContent = (text: string): ContentPart[] => {
        const parts: ContentPart[] = [];
        const codeBlockRegex = /```(\w*)\n([\s\S]*?)```/g;
        let lastIndex = 0;
        let match;
        let blockIndex = 0;

        while ((match = codeBlockRegex.exec(text)) !== null) {
            // Add text before code block
            if (match.index > lastIndex) {
                parts.push({
                    type: 'text', content: text.slice(lastIndex, match.index)
                });
            }

            // Add code block
            parts.push({
                type: 'code', language: match[1] || 'text', content: match[2].trim(), index: blockIndex++
            });

            lastIndex = match.index + match[0].length;
        }

        // Add remaining text
        if (lastIndex < text.length) {
            parts.push({
                type: 'text', content: text.slice(lastIndex)
            });
        }

        return parts;
    };

    const highlightCode = (code: string, language: string): string => {
        // Basit syntax highlighting
        let highlightedCode = code;

        if (language === 'javascript' || language === 'js' || language === 'typescript' || language === 'ts') {
            // Keywords
            highlightedCode = highlightedCode.replace(/\b(function|const|let|var|if|else|for|while|return|import|export|from|interface|type|class|extends)\b/g, '<span class="keyword">$1</span>');

            // Strings
            highlightedCode = highlightedCode.replace(/"([^"\\]|\\.)*"/g, '<span class="string">"$1"</span>');
            highlightedCode = highlightedCode.replace(/'([^'\\]|\\.)*'/g, '<span class="string">\'$1\'</span>');

            // Function names
            highlightedCode = highlightedCode.replace(/\b(\w+)(?=\s*\()/g, '<span class="function">$1</span>');

            // Numbers
            highlightedCode = highlightedCode.replace(/\b\d+\.?\d*\b/g, '<span class="number">$&</span>');

            // Properties
            highlightedCode = highlightedCode.replace(/\.(\w+)/g, '.<span class="property">$1</span>');
        }

        return highlightedCode;
    };

    const renderCodeBlock = (codePart: CodePart) => {
        const isCopied = copiedBlocks.has(codePart.index);
        const highlightedCode = highlightCode(codePart.content, codePart.language);

        return (<div key={codePart.index} className="code-block-container">
            <div className="code-block-header">
                <span className="code-language">{codePart.language}</span>
                <button
                    onClick={() => copyToClipboard(codePart.content, codePart.index)}
                    className="copy-button"
                >
                    {isCopied ? <Check size={14}/> : <Copy size={14}/>}
                    {isCopied ? 'Copied' : 'Copy'}
                </button>
            </div>
            <pre className="code-block">
                    <code dangerouslySetInnerHTML={{__html: highlightedCode}}/>
                </pre>
        </div>);
    };

    const parts = parseContent(content);

    return (<div className={`message-container ${isUser ? 'user' : 'bot'}`}>
        <div className="message-avatar">
            {isUser ? <User size={16}/> : <Bot size={16}/>}
        </div>
        <div className="message-bubble">
            {parts.map((part, index) => {
                if (part.type === 'code') {
                    return renderCodeBlock(part);
                } else {
                    return (<div key={index} className="message-text">
                        {part.content.split('\n').map((line, i) => (<React.Fragment key={i}>
                            {line}
                            {i < part.content.split('\n').length - 1 && <br/>}
                        </React.Fragment>))}
                    </div>);
                }
            })}
        </div>
    </div>);
};

export default Message;