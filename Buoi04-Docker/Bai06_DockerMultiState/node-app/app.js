const express = require('express')
const app = express()
const PORT = process.env.PORT || 3000

app.get('/', (req, res) => {
  res.send('Xin chào từ ứng dụng Node.js trong Docker!')
})

app.listen(PORT, () => {
  console.log(`Server đang chạy trên cổng ${PORT}`)
})