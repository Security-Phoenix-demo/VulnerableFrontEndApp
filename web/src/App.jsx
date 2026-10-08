import React, { useState } from 'react';

import Header from './components/Header';
import FindingsTable from './components/FindingsTable';
import Login from './components/Login';
import { useFindings } from './hooks/useFindings';

export default function App() {
  const [ token, setToken ] = useState(window.localStorage.getItem('token'));
  const { findings, loading, error, search, setSearch } = useFindings(token);

  if (!token) {
    return <Login onToken={ value => { window.localStorage.setItem('token', value); setToken(value); } } />;
  }

  return (
    <div className="app">
      <Header token={ token } onLogout={ () => { window.localStorage.removeItem('token'); setToken(null); } } />
      <main>
        <input placeholder="Search by package" value={ search } onChange={ event => setSearch(event.target.value) } />
        { error && <p className="error">{ error }</p> }
        <FindingsTable rows={ findings } loading={ loading } />
      </main>
    </div>
  );
}
