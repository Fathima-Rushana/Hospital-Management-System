# Mini Hospital Emergency Management System

A console-based Java application built for **CIT300 – Data Structures and Algorithms** (Individual Mid Assignment). The system simulates patient registration, emergency treatment queuing, treatment history tracking, and per-patient visit history — each backed by a data structure implemented from scratch (no `java.util` collection classes used for the core structures).

## Project Overview

The system models a small hospital's emergency workflow:

1. Patients are **registered** and stored in a searchable record store.
2. Arriving patients are placed in an **emergency queue** to wait for treatment.
3. Patients are **treated** in FIFO order, and each completed treatment is logged.
4. Every patient keeps a running **visit history** of past hospital visits.

## Data Structures Used

| # | Requirement | Data Structure | Class | Why |
|---|---|---|---|---|
| 1 | Patient Records | **Binary Search Tree** | `PatientBST.java` | Patient ID is a natural ordering key, giving O(log n) average insert/search/delete plus sorted (in-order) listing. |
| 2 | Emergency Patient Queue | **Queue (FIFO)** — singly linked list | `EmergencyQueue.java` | Patients must be treated in arrival order; a manual linked-list queue avoids array resizing costs and keeps FIFO semantics explicit. |
| 3 | Treatment History | **Stack (LIFO)** — resizable array | `TreatmentStack.java` | The most recently completed treatment is usually what staff want to review first, and a stack naturally models "undo/most-recent-first" access. |
| 4 | Patient Visit History | **Singly Linked List** (one per patient) | `VisitHistory.java` | Each patient's visit count is small and variable; a linked list allows cheap insertion/removal without pre-sizing, and visits are stored in chronological order. |

All structures are implemented manually with their own internal node classes — `java.util.LinkedList`, `Stack`, `Queue`, etc. are **not** used, per assignment requirements.

## Project Structure

```
hospital/
├── Main.java             # Entry point – menu-driven console UI, ties all structures together
├── Patient.java           # Patient record (also owns a VisitHistory)
├── PatientBST.java        # Requirement 1: BST for patient records
├── EmergencyQueue.java     # Requirement 2: FIFO queue for waiting patients
├── TreatmentRecord.java    # Data held in the treatment stack
├── TreatmentStack.java     # Requirement 3: LIFO stack of completed treatments
├── Visit.java              # Data held in a patient's visit history
└── VisitHistory.java       # Requirement 4: Singly linked list of a patient's visits
```

All classes belong to the `hospital` package.

## How to Compile and Run

From the directory containing the `hospital/` package folder:

```bash
# Compile
javac hospital/*.java

# Run
java hospital.Main
```

## Features / Menu Options

```
 1. Register new patient (BST insert)
 2. Search patient by ID (BST search)
 3. Delete patient (BST delete)
 4. Display all patients - ascending ID (BST in-order)
 5. Add patient to emergency queue (Enqueue)
 6. Treat next patient (Dequeue)
 7. Display emergency queue
 8. View treatment history (Stack)
 9. Add visit to patient history (Linked List)
10. View a patient's visit history
11. Remove a visit from patient history
12. Search a visit in patient's history
 0. Exit
```

### Typical workflow
1. Register a patient (option 1) — this stores them in the BST.
2. Add the patient to the emergency queue (option 5).
3. Treat the next patient in line (option 6) — dequeues them and pushes a `TreatmentRecord` onto the stack.
4. View treatment history (option 8) to see completed treatments, most recent first.
5. Add/view/remove/search visits for any registered patient (options 9–12).

## Design Notes

- **BST (`PatientBST`)** keys on `patientId`. Deletion handles all three classic cases: leaf node, single child, and two children (replaced via in-order successor).
- **Queue (`EmergencyQueue`)** uses a singly linked list with `front`/`rear` pointers for O(1) enqueue and dequeue, and prints a friendly message when treating from an empty queue.
- **Stack (`TreatmentStack`)** uses a dynamically resizing array (doubles capacity when full) so it never runs out of space, while still being a manual array-based stack rather than `java.util.Stack`.
- **Linked List (`VisitHistory`)** is maintained per-`Patient` instance, appends at the tail so visits display oldest → newest, and supports removal from the head, middle, or tail.
- Input validation for menu choices is handled in `Main.readInt()`, which re-prompts until a valid integer is entered.

## Author

**J.A.F. Rushana**
Student ID: 23DA2-0500
Individual submission for CIT300 – Data Structures and Algorithms.
