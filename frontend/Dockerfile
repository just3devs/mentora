FROM node:18-alpine

COPY package*.json ./

RUN npm install
COPY src/ ./src/
COPY public/ ./public/
COPY vite.config.* ./
COPY tsconfig.* ./
RUN npm run build

EXPOSE 5173

CMD ["npm", "run", "preview"]
