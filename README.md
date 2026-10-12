# mc-network-watchdog

Paper watchdog signals that warn operators before tick instability becomes player-visible. This project observes health; it does not restart the JVM or kill worlds automatically.

## Design
- Samples Paper tick time on the server scheduler and publishes immutable snapshots.
- Applies a sustained-window threshold to reduce alerts from short save spikes.
- Emits structured console warnings with MSPT, TPS and online-player context.
- Recovery actions remain explicit operator policy.

## Build
Requires Java 21 and Maven. Run `mvn verify`, then install `target/mc-network-watchdog-1.0.0.jar` into `plugins/`.

## Configuration
```yaml
sampling:
  interval-ticks: 100
  warning-mspt: 40
  critical-mspt: 48
  sustained-samples: 3
```

Thresholds are starting points, not a capacity guarantee. Benchmark on representative hardware and workloads. MIT licensed.
