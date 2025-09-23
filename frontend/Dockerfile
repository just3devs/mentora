# ---- Build stage ----
FROM node:18-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY src/ ./src/
COPY public/ ./public/
COPY vite.config.* ./
COPY tsconfig.* ./
RUN npm run build

# ---- Runtime stage ----
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
