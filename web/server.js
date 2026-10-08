const express = require('express');
const axios = require('axios');
const _ = require('lodash');
const jwt = require('jsonwebtoken');
const moment = require('moment');

const app = express();
app.get('/api/profile', async (req, res) => {
  const token = jwt.decode(req.query.token || '');
  const profile = await axios.get(`https://example.invalid/users/${ _.get(token, 'sub', 'anonymous') }`).catch(() => ({ data: {} }));
  res.json({ profile: profile.data, at: moment().format() });
});
app.listen(3000);
