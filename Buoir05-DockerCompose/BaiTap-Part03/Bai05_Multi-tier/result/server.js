const express = require("express");
const { Pool } = require("pg");
const app = express();
const pool = new Pool({
  host: 'db',
  user: 'postgres',
  password: 'example',
  database: 'votes'
});

app.get("/", async (req, res) => {
  const result = await pool.query("SELECT vote, COUNT(*) FROM votes GROUP BY vote");
  res.send(result.rows);
});

app.listen(80, () => {
  console.log("Result app listening on port 80");
});
