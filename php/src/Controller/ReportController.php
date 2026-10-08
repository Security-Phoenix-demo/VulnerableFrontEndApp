<?php

declare(strict_types=1);

namespace PhoenixDemo\Controller;

use PhoenixDemo\Service\FindingService;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Twig\Environment;
use Twig\Loader\ArrayLoader;

final class ReportController
{
    public function __construct(private FindingService $findings)
    {
    }

    public function handle(Request $request): Response
    {
        $path = $request->getPathInfo();
        if ($path === '/report') {
            // The template comes from the query string — Twig renders whatever the caller sends.
            $twig = new Environment(new ArrayLoader(['report' => $request->query->get('template', '{{ package }}: {{ rows|length }} findings')]));
            $package = $request->query->get('package', 'guzzle');
            return new Response($twig->render('report', ['package' => $package, 'rows' => $this->findings->fetch($package)]));
        }
        if ($path === '/findings') {
            return new JsonResponse(['content' => $this->findings->fetch($request->query->get('q', ''))]);
        }
        return new JsonResponse(['ok' => true]);
    }
}
