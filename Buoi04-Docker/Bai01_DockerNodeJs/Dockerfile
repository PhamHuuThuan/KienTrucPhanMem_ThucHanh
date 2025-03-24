#cai dat moi truong
FROM node:18

#Tao thu muc trong container
WORKDIR /usr/src/app

#Sao chep package
COPY package*.json ./

#Cai dat npm
RUN npm install

#Sao chep ma nguon
COPY app.js ./

#Expose cong 3000
EXPOSE 3000

#Chay ung dung
CMD ["node", "app.js"]