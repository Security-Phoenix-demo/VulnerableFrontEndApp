import { useEffect, useState } from 'react';
import debounce from 'lodash/debounce';

import { fetchFindings } from '../api/client';

export function useFindings(token) {
  const [ findings, setFindings ] = useState([]);
  const [ search, setSearch ] = useState('');
  const [ loading, setLoading ] = useState(false);
  const [ error, setError ] = useState(null);

  useEffect(() => {
    if (!token) return undefined;
    const load = debounce(async () => {
      setLoading(true);
      setError(null);
      try {
        setFindings(await fetchFindings(token, search));
      } catch (err) {
        setError(err.response?.data?.error ?? err.message);
      } finally {
        setLoading(false);
      }
    }, 300);
    load();
    return () => load.cancel();
  }, [ token, search ]);

  return { findings, loading, error, search, setSearch };
}
