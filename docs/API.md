# API Documentation

This document describes the RESTful API endpoints available in the Post Office Management System.

## Base URL

```
http://localhost:8080
```

## Endpoints

### Dashboard

#### Get Dashboard
Returns the main dashboard view with statistics.

```
GET /
```

**Response:** HTML page (dashboard.html)

---

### Parcels

#### Get All Parcels
Retrieve a list of all parcels.

```
GET /parcels
```

**Response:** HTML page (parcels.html) with parcel list

---

#### Get Parcel by ID
Retrieve details of a specific parcel.

```
GET /api/parcels/{parcelId}
```

**Path Parameters:**
- `parcelId` (String): The unique identifier of the parcel

**Success Response:**
```json
{
  "parcelId": "PCL001",
  "senderName": "John Doe",
  "senderAddress": "123 Main St, City",
  "receiverName": "Jane Smith",
  "receiverAddress": "456 Oak Ave, Town",
  "weight": 2.5,
  "category": "PACKAGE",
  "current_status": "IN_TRANSIT",
  "createdDate": "2025-11-25T10:30:00",
  "qrCodePath": "/static/qrcodes/PCL001.png",
  "assignedOffice": {
    "officeId": "PO001",
    "officeName": "Central Post Office",
    "location": "Downtown",
    "contactNumber": "555-0100"
  },
  "history": [
    {
      "id": 1,
      "status": "REGISTERED",
      "updateTime": "2025-11-25T10:30:00",
      "location": "Central Post Office"
    }
  ]
}
```

**Error Response:**
```json
{
  "error": "Parcel not found: PCL001"
}
```

---

#### Add New Parcel
Create a new parcel in the system.

```
POST /api/parcels
```

**Request Body:**
```json
{
  "parcelId": "PCL001",
  "senderName": "John Doe",
  "senderAddress": "123 Main St, City",
  "receiverName": "Jane Smith",
  "receiverAddress": "456 Oak Ave, Town",
  "weight": 2.5,
  "category": "PACKAGE",
  "current_status": "REGISTERED",
  "assignedOffice": {
    "officeId": "PO001"
  }
}
```

**Field Descriptions:**
- `parcelId` (String, required): Unique parcel identifier
- `senderName` (String, required): Name of the sender
- `senderAddress` (String, required): Sender's address
- `receiverName` (String, required): Name of the receiver
- `receiverAddress` (String, required): Receiver's address
- `weight` (Double, required): Parcel weight in kg
- `category` (Enum, required): One of: DOCUMENT, PACKAGE, REGISTERED, EMS
- `current_status` (Enum, required): Initial status (typically REGISTERED)
- `assignedOffice.officeId` (String, optional): ID of the post office handling the parcel

**Success Response:**
```json
{
  "parcelId": "PCL001",
  "senderName": "John Doe",
  "qrCodePath": "/static/qrcodes/PCL001.png",
  ...
}
```

**Note:** A QR code is automatically generated for the parcel.

---

#### Update Parcel
Update an existing parcel's details.

```
PUT /api/parcels/{parcelId}
```

**Path Parameters:**
- `parcelId` (String): The unique identifier of the parcel

**Request Body:** Same as Add Parcel

**Success Response:** Updated parcel object

**Note:** Updates automatically create a new history entry.

---

#### Delete Parcel
Remove a parcel from the system.

```
DELETE /api/parcels/{parcelId}
```

**Path Parameters:**
- `parcelId` (String): The unique identifier of the parcel

**Success Response:**
```json
{
  "message": "Parcel deleted successfully"
}
```

---

### Post Offices

#### Get All Post Offices
Retrieve a list of all post offices.

```
GET /post-offices
```

**Response:** HTML page (post-offices.html) with office list

---

#### Get Post Office by ID
Retrieve details of a specific post office.

```
GET /api/post-offices/{officeId}
```

**Path Parameters:**
- `officeId` (String): The unique identifier of the post office

**Success Response:**
```json
{
  "officeId": "PO001",
  "officeName": "Central Post Office",
  "location": "123 Main St, Downtown",
  "contactNumber": "555-0100"
}
```

---

#### Add New Post Office
Create a new post office.

```
POST /api/post-offices
```

**Request Body:**
```json
{
  "officeId": "PO001",
  "officeName": "Central Post Office",
  "location": "123 Main St, Downtown",
  "contactNumber": "555-0100"
}
```

**Field Descriptions:**
- `officeId` (String, required): Unique office identifier
- `officeName` (String, required): Name of the post office
- `location` (String, required): Physical address
- `contactNumber` (String, required): Contact phone number

**Success Response:** Created post office object

---

#### Update Post Office
Update an existing post office's details.

```
PUT /api/post-offices/{officeId}
```

**Path Parameters:**
- `officeId` (String): The unique identifier of the post office

**Request Body:** Same as Add Post Office

**Success Response:** Updated post office object

---

#### Delete Post Office
Remove a post office from the system.

```
DELETE /api/post-offices/{officeId}
```

**Path Parameters:**
- `officeId` (String): The unique identifier of the post office

**Success Response:**
```json
{
  "message": "Post Office deleted successfully"
}
```

---

### QR Scanner & Tracking

#### QR Checkpoint Page
Access the QR code scanning interface.

```
GET /qr-checkpoint
```

**Response:** HTML page (qr-checkpoint.html)

---

#### Tracking Page
Access the parcel tracking interface.

```
GET /tracking
```

**Response:** HTML page (tracking.html)

---

#### Scan Parcel (Update via QR)
Update parcel status using QR code data.

```
POST /api/qr/scan
```

**Request Body:**
```json
{
  "parcelId": "PCL001",
  "newStatus": "IN_TRANSIT",
  "location": "Regional Hub"
}
```

**Field Descriptions:**
- `parcelId` (String, required): Parcel ID from QR code
- `newStatus` (Enum, required): New status to set
- `location` (String, required): Current location

**Valid Status Values:**
- `REGISTERED`
- `AT_ORIGIN_POST_OFFICE`
- `IN_TRANSIT`
- `AT_DESTINATION_POST_OFFICE`
- `OUT_FOR_DELIVERY`
- `DELIVERED`
- `RETURNED`

**Success Response:**
```json
{
  "message": "Parcel status updated successfully",
  "parcel": {
    "parcelId": "PCL001",
    "current_status": "IN_TRANSIT"
  }
}
```

---

## Data Models

### Parcel Category Enum
```
DOCUMENT    - Letters and documents
PACKAGE     - Standard packages
REGISTERED  - Registered mail
EMS         - Express Mail Service
```

### Parcel Status Enum
```
REGISTERED                  - Parcel registered in system
AT_ORIGIN_POST_OFFICE      - At sender's post office
IN_TRANSIT                  - Moving between locations
AT_DESTINATION_POST_OFFICE - Arrived at receiver's post office
OUT_FOR_DELIVERY           - Out for final delivery
DELIVERED                   - Successfully delivered
RETURNED                    - Returned to sender
```

## Error Handling

All API endpoints return appropriate HTTP status codes:

- `200 OK` - Successful GET request
- `201 Created` - Successful POST request
- `204 No Content` - Successful DELETE request
- `400 Bad Request` - Invalid request data
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

**Error Response Format:**
```json
{
  "timestamp": "2025-11-25T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Parcel not found: PCL001",
  "path": "/api/parcels/PCL001"
}
```

## Rate Limiting

Currently, there are no rate limits implemented. However, please use the API responsibly.

## Authentication

The current version does not include authentication. Future versions may include:
- JWT-based authentication
- Role-based access control (Admin, Operator, Viewer)
- API keys for external integrations

## Examples

### Using cURL

**Get a parcel:**
```bash
curl http://localhost:8080/api/parcels/PCL001
```

**Create a parcel:**
```bash
curl -X POST http://localhost:8080/api/parcels \
  -H "Content-Type: application/json" \
  -d '{
    "parcelId": "PCL001",
    "senderName": "John Doe",
    "senderAddress": "123 Main St",
    "receiverName": "Jane Smith",
    "receiverAddress": "456 Oak Ave",
    "weight": 2.5,
    "category": "PACKAGE",
    "current_status": "REGISTERED"
  }'
```

**Update parcel status:**
```bash
curl -X POST http://localhost:8080/api/qr/scan \
  -H "Content-Type: application/json" \
  -d '{
    "parcelId": "PCL001",
    "newStatus": "IN_TRANSIT",
    "location": "Regional Hub"
  }'
```

### Using JavaScript (Fetch API)

**Get a parcel:**
```javascript
fetch('http://localhost:8080/api/parcels/PCL001')
  .then(response => response.json())
  .then(data => console.log(data))
  .catch(error => console.error('Error:', error));
```

**Create a parcel:**
```javascript
fetch('http://localhost:8080/api/parcels', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({
    parcelId: 'PCL001',
    senderName: 'John Doe',
    senderAddress: '123 Main St',
    receiverName: 'Jane Smith',
    receiverAddress: '456 Oak Ave',
    weight: 2.5,
    category: 'PACKAGE',
    current_status: 'REGISTERED'
  })
})
  .then(response => response.json())
  .then(data => console.log(data))
  .catch(error => console.error('Error:', error));
```

## Future Enhancements

Planned API improvements:
- Pagination for large datasets
- Advanced filtering and search
- Bulk operations
- WebSocket for real-time updates
- Export functionality (CSV, PDF)
- Statistics and reporting endpoints

---

**Need Help?** Open an issue on GitHub or refer to the main [README](../README.md).
