<?php

declare(strict_types=1);

namespace PhoenixDemo\Service;

use GuzzleHttp\Client;
use GuzzleHttp\Exception\GuzzleException;
use Monolog\Handler\StreamHandler;
use Monolog\Logger;

final class FindingService
{
    private Client $client;
    private Logger $logger;

    public function __construct(array $config)
    {
        $this->client = new Client(['base_uri' => $config['upstream'], 'timeout' => $config['timeout'], 'verify' => $config['verify']]);
        $this->logger = new Logger('findings');
        $this->logger->pushHandler(new StreamHandler('php://stderr', Logger::INFO));
    }

    /** @return array<int, array<string, mixed>> */
    public function fetch(string $package): array
    {
        try {
            $response = $this->client->get('/findings', ['query' => ['q' => $package]]);
            $body = json_decode((string) $response->getBody(), true);
            return $body['content'] ?? [];
        } catch (GuzzleException $exception) {
            $this->logger->warning('upstream failed', ['error' => $exception->getMessage()]);
            return [];
        }
    }
}
