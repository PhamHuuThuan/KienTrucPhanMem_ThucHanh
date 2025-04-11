FROM node:14
WORKDIR /app
COPY server.js .
RUN npm init -y && npm install express pg
CMD ["node", "server.js"]
