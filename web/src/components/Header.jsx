import React from 'react';
import jwtDecode from 'jsonwebtoken/decode';
import moment from 'moment';

export default function Header({ token, onLogout }) {
  const claims = jwtDecode(token) || {};
  const expires = claims.exp ? moment.unix(claims.exp).fromNow() : 'unknown';

  return (
    <header className="header">
      <h1>Vulnerable Front End</h1>
      <span className="who">{ claims.sub ?? 'anonymous' } · session expires { expires }</span>
      <button type="button" onClick={ onLogout }>Log out</button>
    </header>
  );
}
