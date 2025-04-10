const express = require('express');
const mongoose = require('mongoose');
const app = express();

mongoose.connect(process.env.MONGODB_URI);

const Item = mongoose.model('Item', new mongoose.Schema({ name: String }));

app.get('/', async (req, res) => {
  await Item.create({ name: 'Test Item' });
  const items = await Item.find();
  res.json(items);
});

app.listen(3000, () => console.log('Server running on port 3000'));