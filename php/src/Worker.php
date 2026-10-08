<?php

namespace PhoenixDemo;

use GuzzleHttp\Client;
use Symfony\Component\HttpFoundation\Request;

final class Worker
{
    public function run(Request $request): string
    {
        $client = new Client();
        $response = $client->get($request->query->get('url', 'https://example.invalid'));
        return (string) $response->getBody();
    }
}
