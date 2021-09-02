
# Introduction

MovieRentals was built from the ground-up it returns a JSON that makes it easy for developers and sysadmins to consume easily.

These docs describe how to use the MovieRental API. We hope you enjoy these docs.



## Use Cases

There are many reasons to use the MovieRentals API. The most common use cases are to addMovies,finadAllMovies and RentMovies.

## Authorization
The movieRentals Api requires no authorization

The movieRentalsApi Are as follows

```http
Post /videoRental/save
Get /videoRental/listAllVideos
Get /videoRental/findVideoById/{id}
Get /videoRental/calculateVideoPrice

```

| Parameter | Type | Description |
| :--- | :--- | :--- |
| `{id}` | `string` | **Required**. The title of your movie|


## Responses

Many API endpoints return the JSON representation of the resources created or edited. However, if an invalid request is submitted, or some other error occurs, movieRentals returns a JSON response in the following format:
```javascript
{
  "message" : string,
  "data"    : string
}
```

The `message` attribute contains a message commonly used to indicate errors or, in the case of deleting a resource, success that the resource was properly deleted.

The `data` attribute contains any other metadata associated with the response. This will be an escaped string containing JSON data.

## Status Codes

movieRentals returns the following status codes in its API:

| Status Code | Description |
| :--- | :--- |
| 200 | `OK` |
| 400 | `BAD REQUEST` |
