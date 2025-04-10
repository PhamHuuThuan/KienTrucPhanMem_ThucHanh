const express = require('express');
const mysql = require('mysql2/promise');
const app = express();

const {
  DB_HOST = 'mysql', 
  DB_PORT = 3306, 
  DB_USER = 'user',
  DB_PASSWORD = 'password',
  DB_NAME = 'mydb'
} = process.env;

// Kết nối MySQL 
async function initDB() {
  const connection = await mysql.createConnection({
    host: DB_HOST,  
    port: DB_PORT,
    user: DB_USER,
    password: DB_PASSWORD,
    database: DB_NAME
  });
  
  await connection.query(`
    CREATE TABLE IF NOT EXISTS users (
      id INT AUTO_INCREMENT PRIMARY KEY,
      name VARCHAR(255) NOT NULL
    )
  `);
  
  return connection;
}

// API endpoint
app.get('/', async (req, res) => {
  try {
    const conn = await initDB();
    const [rows] = await conn.query('SELECT * FROM users');
    res.json(rows);
  } catch (err) {
    res.status(500).send(err.message);
  }
});

app.listen(3000, () => {
  console.log('Node app running on port 3000');
});