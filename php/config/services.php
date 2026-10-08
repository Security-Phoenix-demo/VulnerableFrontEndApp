<?php

return [
    'upstream' => getenv('REPORT_UPSTREAM') ?: 'https://example.invalid/reports',
    'timeout' => 5,
    'verify' => false,
];
