## Motorsport REST API Documentation
# Introduction

Welcome to the Motorsport REST API documentation. This API is designed to provide secure endpoints for managing and advertising vehicles in the motorsport industry. Users can interact with various features, such as creating, updating, liking vehicles, and more.
# Authentication
# Sign Up

    Endpoint: POST /auth/signup
    Description: Register a new user. 
    Request Body:
        firstname (String): First name of the user.
        lastname (String): Last name of the user.
        email (String): User's email address.
        password (String): User's password.

# Login

    Endpoint: POST /auth/login
    Description: Log in to the system.
    Request Body:
        email (String): User's email address.
        password (String): User's password.
    Response:
        JWT token for authentication.

# User Operations
# Get User

    Endpoint: GET /api/user/v1/get/{user_id}
    Description: Retrieve information about a specific user.
    Path Parameters:
        user_id (Integer): ID of the user.

# Vehicle Operations
# Create Vehicle

    Endpoint: POST /vehicle/api/v1/create/user/{user_id}
    Description: Create a new vehicle.
    Path Parameters:
        user_id (Integer): ID of the user creating the vehicle.
    Request Body:
        firstname (String): First name of the vehicle owner.
        lastname (String): Last name of the vehicle owner.
        Other vehicle details.

# Get all Vehicles

    Endpoint: GET /api/vehicle/v1/get
    Description: Retrieve a list of all vehicles.

# Get Vehicle by ID

    Endpoint: GET /api/vehicle/v1/get/{vehicle_id}
    Description: Retrieve information about a specific vehicle.
    Path Parameters:
        vehicle_id (Integer): ID of the vehicle.

# Update Vehicle

    Endpoint: PUT /api/vehicle/v1/update/{vehicle_id}
    Description: Update information for a specific vehicle.
    Path Parameters:
        vehicle_id (Integer): ID of the vehicle.
    Request Body:
        Updated vehicle details.

# Delete Vehicle

    Endpoint: DELETE /api/vehicle/v1/delete/{vehicle_id}
    Description: Delete a specific vehicle.
    Path Parameters:
        vehicle_id (Integer): ID of the vehicle.

# Like or Unlike Vehicle

    Endpoint: POST /api/vehicle/v1/like/vehicle/{vehicle_id}/user/{user_id}
    Description: Like or Unlike a specific vehicle.
    Path Parameters:
        vehicle_id (Integer): ID of the vehicle.
        user_id (Integer): ID of the user.

# Pagination with Sorting

    Endpoint: GET /api/vehicle/v1/pagination/{offset}/{page_size}?field={field_name}
    Description: Retrieve paginated and sorted list of vehicles.
    Path Parameters:
        offset (Integer): Starting index for pagination.
        page_size (Integer): Number of items per page.
    Query Parameters:
        field_name (String): Name of the field to sort by.

This documentation provides an overview of the available endpoints and their functionalities. Make sure to include detailed information about request and response structures, error handling, and any additional features specific to your implementation.
