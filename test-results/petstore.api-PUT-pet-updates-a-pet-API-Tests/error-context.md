# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: petstore.api.spec.ts >> PUT /pet updates a pet
- Location: tests\petstore.api.spec.ts:35:5

# Error details

```
Error: expect(received).toContain(expected) // indexOf

Expected value: 405
Received array: [200, 201]
```

# Test source

```ts
  1  | import { test, expect, APIResponse } from '@playwright/test';
  2  | 
  3  | // GET /pet/findByStatus
  4  | // Returns pets with status available
  5  | 
  6  | test('GET /pet/findByStatus returns available pets', async ({ request }) => {
  7  |   const response = await request.get('/pet/findByStatus?status=available');
  8  |   expect(response.ok()).toBeTruthy();
  9  |   const pets = await response.json();
  10 |   expect(Array.isArray(pets)).toBeTruthy();
  11 | });
  12 | 
  13 | // POST /pet
  14 | // Adds a new pet to the store
  15 | 
  16 | test('POST /pet creates a new pet', async ({ request }) => {
  17 |   const newPet = {
  18 |     id: Date.now(),
  19 |     name: 'TestPet',
  20 |     photoUrls: ['http://example.com/photo.jpg'],
  21 |     status: 'available',
  22 |   };
  23 |   const response = await request.post('/pet', {
  24 |     json: newPet,
  25 |     headers: { 'Content-Type': 'application/json' }
  26 |   });
  27 |   expect([200, 201]).toContain(response.status());
  28 |   const pet = await response.json();
  29 |   expect(pet.name).toBe('TestPet');
  30 | });
  31 | 
  32 | // PUT /pet
  33 | // Updates an existing pet
  34 | 
  35 | test('PUT /pet updates a pet', async ({ request }) => {
  36 |   // First, create a pet to update
  37 |   const newPet = {
  38 |     id: Date.now() + 1,
  39 |     name: 'PetToUpdate',
  40 |     photoUrls: ['http://example.com/photo.jpg'],
  41 |     status: 'available',
  42 |   };
  43 |   await request.post('/pet', {
  44 |     json: newPet,
  45 |     headers: { 'Content-Type': 'application/json' }
  46 |   });
  47 |   // Now update
  48 |   const updatePet = {
  49 |     ...newPet,
  50 |     name: 'UpdatedPet',
  51 |     status: 'sold',
  52 |   };
  53 |   const response = await request.put('/pet', {
  54 |     json: updatePet,
  55 |     headers: { 'Content-Type': 'application/json' }
  56 |   });
> 57 |   expect([200, 201]).toContain(response.status());
     |                      ^ Error: expect(received).toContain(expected) // indexOf
  58 |   const pet = await response.json();
  59 |   expect(pet.status).toBe('sold');
  60 | });
  61 | 
  62 | // DELETE /pet/{petId}
  63 | // Deletes a pet by ID
  64 | 
  65 | test('DELETE /pet/{petId} deletes a pet', async ({ request }) => {
  66 |   // First, create a pet to delete
  67 |   const newPet = {
  68 |     id: Date.now() + 2,
  69 |     name: 'PetToDelete',
  70 |     photoUrls: ['http://example.com/photo.jpg'],
  71 |     status: 'available',
  72 |   };
  73 |   await request.post('/pet', {
  74 |     json: newPet,
  75 |     headers: { 'Content-Type': 'application/json' }
  76 |   });
  77 |   // Now delete
  78 |   const response = await request.delete(`/pet/${newPet.id}`, {
  79 |     headers: { 'Content-Type': 'application/json' }
  80 |   });
  81 |   expect([200, 204]).toContain(response.status());
  82 | });
  83 | 
```