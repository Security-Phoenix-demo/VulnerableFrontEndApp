import React from 'react';
import sortBy from 'lodash/sortBy';

const SEVERITY_ORDER = { critical: 0, high: 1, medium: 2, low: 3 };

export default function FindingsTable({ rows, loading }) {
  if (loading) return <p>Loading findings…</p>;
  if (!rows.length) return <p>No findings match.</p>;

  const sorted = sortBy(rows, row => [ SEVERITY_ORDER[row.severity] ?? 9, -row.cvssScore ]);

  return (
    <table className="findings">
      <thead>
        <tr><th>Package</th><th>Installed</th><th>Fixed in</th><th>CVE</th><th>CVSS</th></tr>
      </thead>
      <tbody>
        { sorted.map(row => (
          <tr key={ row.id } className={ row.severity }>
            <td>{ row.package?.name ?? row.packageName }</td>
            <td>{ row.package?.version ?? row.installedVersion }</td>
            <td>{ row.fixedVersion ?? '—' }</td>
            <td><a href={ `https://nvd.nist.gov/vuln/detail/${ row.cve }` } target="_blank" rel="noreferrer">{ row.cve }</a></td>
            <td>{ row.cvssScore }</td>
          </tr>
        )) }
      </tbody>
    </table>
  );
}
