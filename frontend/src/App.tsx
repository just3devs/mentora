import { useState, useEffect } from "react";

function App() {
    const [mounted, setMounted] = useState(false);

    useEffect(() => {
        setMounted(true);
    }, []);

    // Yıldızları önceden oluştur
    const stars = Array.from({ length: 200 }, (_, i) => ({
        id: i,
        left: Math.random() * 100,
        top: Math.random() * 100,
        delay: Math.random() * 5,
        duration: 2 + Math.random() * 3,
        size: Math.random() > 0.7 ? 3 : Math.random() > 0.4 ? 2 : 1
    }));

    return (
        <>
            <style>{`
                * {
                    margin: 0;
                    padding: 0;
                    box-sizing: border-box;
                }
                
                html, body, #root {
                    width: 100%;
                    height: 100%;
                    background: #000 !important;
                    overflow: hidden;
                }
                
                @keyframes twinkle {
                    0%, 100% { opacity: 0.3; transform: scale(1); }
                    50% { opacity: 1; transform: scale(1.5); }
                }
                
                @keyframes gradient-move {
                    0% { background-position: 0% 50%; }
                    50% { background-position: 100% 50%; }
                    100% { background-position: 0% 50%; }
                }
                
                @keyframes float {
                    0%, 100% { transform: translateY(0px) rotate(0deg); }
                    50% { transform: translateY(-15px) rotate(3deg); }
                }
                
                @keyframes pulse-glow {
                    0%, 100% { 
                        box-shadow: 0 0 20px rgba(139, 92, 246, 0.5), 0 0 40px rgba(236, 72, 153, 0.3);
                        transform: scale(1);
                    }
                    50% { 
                        box-shadow: 0 0 40px rgba(139, 92, 246, 0.8), 0 0 80px rgba(236, 72, 153, 0.5);
                        transform: scale(1.1);
                    }
                }
                
                .gradient-text {
                    background: linear-gradient(45deg, #06b6d4, #8b5cf6, #ec4899, #f59e0b);
                    background-size: 400% 400%;
                    -webkit-background-clip: text;
                    background-clip: text;
                    -webkit-text-fill-color: transparent;
                    animation: gradient-move 4s ease-in-out infinite;
                    text-shadow: 0 0 30px rgba(139, 92, 246, 0.8);
                    filter: drop-shadow(0 0 30px rgba(139, 92, 246, 0.8));
                }
                
                .gradient-text-2 {
                    background: linear-gradient(45deg, #ec4899, #f59e0b, #ef4444, #06b6d4);
                    background-size: 400% 400%;
                    -webkit-background-clip: text;
                    background-clip: text;
                    -webkit-text-fill-color: transparent;
                    animation: gradient-move 3s ease-in-out infinite reverse;
                    filter: drop-shadow(0 0 20px rgba(236, 72, 153, 0.8));
                }
            `}</style>

            <div style={{
                width: '100vw',
                height: '100vh',
                background: '#000',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                position: 'relative',
                overflow: 'hidden'
            }}>
                {/* Renkli background blobs */}
                <div style={{position: 'absolute', inset: 0}}>
                    <div style={{
                        position: 'absolute',
                        top: '40px',
                        left: '40px',
                        width: '320px',
                        height: '320px',
                        background: 'rgba(147, 51, 234, 0.3)',
                        borderRadius: '50%',
                        filter: 'blur(60px)',
                        animation: 'pulse-glow 4s ease-in-out infinite'
                    }}></div>
                    <div style={{
                        position: 'absolute',
                        top: '80px',
                        right: '40px',
                        width: '320px',
                        height: '320px',
                        background: 'rgba(6, 182, 212, 0.3)',
                        borderRadius: '50%',
                        filter: 'blur(60px)',
                        animation: 'pulse-glow 4s ease-in-out infinite',
                        animationDelay: '1s'
                    }}></div>
                    <div style={{
                        position: 'absolute',
                        bottom: '40px',
                        left: '33%',
                        width: '320px',
                        height: '320px',
                        background: 'rgba(236, 72, 153, 0.3)',
                        borderRadius: '50%',
                        filter: 'blur(60px)',
                        animation: 'pulse-glow 4s ease-in-out infinite',
                        animationDelay: '2s'
                    }}></div>
                    <div style={{
                        position: 'absolute',
                        bottom: '80px',
                        right: '25%',
                        width: '240px',
                        height: '240px',
                        background: 'rgba(245, 158, 11, 0.2)',
                        borderRadius: '50%',
                        filter: 'blur(60px)',
                        animation: 'pulse-glow 4s ease-in-out infinite',
                        animationDelay: '3s'
                    }}></div>
                </div>

                {/* Yıldızlar */}
                {stars.map((star) => (
                    <div
                        key={star.id}
                        style={{
                            position: 'absolute',
                            left: `${star.left}%`,
                            top: `${star.top}%`,
                            width: `${star.size}px`,
                            height: `${star.size}px`,
                            background: star.size === 3 ? '#06b6d4' : star.size === 2 ? '#8b5cf6' : '#ffffff',
                            borderRadius: '50%',
                            animation: `twinkle ${star.duration}s ease-in-out infinite`,
                            animationDelay: `${star.delay}s`,
                            boxShadow: star.size === 3 ? '0 0 10px #06b6d4' : star.size === 2 ? '0 0 8px #8b5cf6' : '0 0 4px #ffffff'
                        }}
                    />
                ))}

                {/* Ana içerik */}
                <div style={{
                    textAlign: 'center',
                    zIndex: 10,
                    position: 'relative',
                    padding: '0 20px'
                }}>
                    <div
                        style={{
                            opacity: mounted ? 1 : 0,
                            transform: mounted ? 'scale(1) translateY(0)' : 'scale(0.8) translateY(30px)',
                            transition: 'all 1.5s ease-out',
                            animation: mounted ? 'float 6s ease-in-out infinite' : 'none'
                        }}
                    >
                        <h1
                            className="gradient-text"
                            style={{
                                fontSize: 'clamp(4rem, 10vw, 10rem)',
                                fontWeight: 900,
                                margin: '0 0 20px 0',
                                lineHeight: 0.9
                            }}
                        >
                            Mentora
                        </h1>
                        <h2
                            className="gradient-text-2"
                            style={{
                                fontSize: 'clamp(2.5rem, 7vw, 7rem)',
                                fontWeight: 700,
                                margin: 0,
                                lineHeight: 1
                            }}
                        >
                            is here
                        </h2>
                    </div>

                    {/* Merkez parlama efekti */}
                    <div style={{
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        width: '8px',
                        height: '8px',
                        background: '#ffffff',
                        borderRadius: '50%',
                        transform: 'translate(-50%, -50%)',
                        animation: 'pulse-glow 2s ease-in-out infinite',
                        opacity: 0.8
                    }}></div>
                </div>
            </div>
        </>
    );
}

export default App;