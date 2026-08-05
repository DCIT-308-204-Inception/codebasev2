# Graph Trace Notes for the Sample Network

## Sample edges

| Edge | Weight |
|---|---:|
| ENT--ISS | 2 |
| ENT--REF | 6 |
| ISS--REF | 3 |
| ISS--ICT | 5 |
| REF--ICT | 2 |
| REF--PER | 4 |
| ICT--PER | 1 |
| ICT--ARC | 7 |
| PER--ARC | 3 |

Because each new adjacency-list edge is inserted at the head, the neighbour
order is determined by reverse insertion order.

## BFS from ENT

| Step | Dequeued | Newly discovered | Queue after step |
|---:|---|---|---|
| 1 | ENT | REF, ISS | REF, ISS |
| 2 | REF | PER, ICT | ISS, PER, ICT |
| 3 | ISS | none | PER, ICT |
| 4 | PER | ARC | ICT, ARC |
| 5 | ICT | none | ARC |
| 6 | ARC | none | empty |

Traversal: `ENT, REF, ISS, PER, ICT, ARC`

## Dijkstra from ENT to ARC

| Settled vertex | Final distance | Important relaxations |
|---|---:|---|
| ENT | 0 | ISS=2, REF=6 |
| ISS | 2 | REF=5, ICT=7 |
| REF | 5 | PER=9; ICT remains 7 (equal-cost alternative) |
| ICT | 7 | PER=8, ARC=14 |
| PER | 8 | ARC=11 |
| ARC | 11 | destination reached |

Predecessor chain:
`ARC <- PER <- ICT <- ISS <- ENT`

Shortest path:
`ENT -> ISS -> ICT -> PER -> ARC`

Total weight: `11`

## MST result

One valid MST is:

- ENT--ISS (2)
- ISS--REF (3)
- REF--ICT (2)
- ICT--PER (1)
- PER--ARC (3)

Total weight: `11`

Prim and Kruskal may select edges in different orders, but they must produce
the same minimum total weight for this sample.


## Equal-cost shortest-path note

There is also an equal-cost route
`ENT -> ISS -> REF -> ICT -> PER -> ARC` with total weight `11`.
The implementation only replaces a predecessor when it finds a strictly
smaller distance, so it keeps the first weight-11 route discovered. Both routes
are correct shortest paths.
