FROM node:18-alpine

COPY package*.json ./

RUN npm install

RUN npm run build

EXPOSE 5173

CMD ["npm", "run", "preview"]
