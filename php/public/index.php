<?php

declare(strict_types=1);

require __DIR__ . '/../vendor/autoload.php';

use PhoenixDemo\Controller\ReportController;
use PhoenixDemo\Service\FindingService;
use Symfony\Component\HttpFoundation\Request;

$request = Request::createFromGlobals();
$controller = new ReportController(new FindingService(require __DIR__ . '/../config/services.php'));

$response = $controller->handle($request);
$response->send();
