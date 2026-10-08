import React from 'react';
import ReactDOM from 'react-dom';
import _ from 'lodash';

function App() {
  const items = _.uniq([ 'alerts', 'findings', 'remedies' ]);
  return <ul>{ items.map(item => <li key={ item }>{ item }</li>) }</ul>;
}

ReactDOM.render(<App />, document.getElementById('root'));
