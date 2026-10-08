import React, { useState } from 'react';

import { login } from '../api/client';

export default function Login({ onToken }) {
  const [ username, setUsername ] = useState('admin');
  const [ password, setPassword ] = useState('');
  const [ error, setError ] = useState(null);

  async function submit(event) {
    event.preventDefault();
    try {
      onToken(await login(username, password));
    } catch (err) {
      setError('Login failed');
    }
  }

  return (
    <form className="login" onSubmit={ submit }>
      <label>Username <input value={ username } onChange={ e => setUsername(e.target.value) } /></label>
      <label>Password <input type="password" value={ password } onChange={ e => setPassword(e.target.value) } /></label>
      { error && <p className="error">{ error }</p> }
      <button type="submit">Sign in</button>
    </form>
  );
}
