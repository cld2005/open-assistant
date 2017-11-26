# Open Assistant

An open source virtual assistant platform

## TL;DR

On Open Assistant platform, an app is one individual virtual assistant developed for different purposes. All apps are either deployed onto the same cloud database then distributed (like an App Store) or installed locally on the user's device.

## Architecture

![Architecture](docs/architecture.png?raw=true "Architecture")

There are three main parts provided by Open Assisent.

### 1. Client SDK

Client here means different kinds of apps running on the user's device such as Android App, iOS App, etc. Client SDK is provided for these apps, and the design goal of the SDK is to:

- send and receive messages to/from the Gateway,
- execute operations sent by the App,
- authenticate the client with the Gateway,
- execute local apps, and
- provide TTS and voice recognizations.

### 2. Gateway

Gateway acts like as the entry point for both the client and the app. Its responsibility includes:

- forward messages to the other side,
- authenticate the user,
- block abuse usages,
- manage the state of application containers, and
- scale in/out the application containers.

### 3. App SDK

App are developed by applciation providers and App SDK is provided for them access Open Assistent resources. App is first be containerized and stored into database, then be executed in the cluster by the Gateway. App containers are **stateless** and are allowed to use any 3rd-party library to connect to external services such as the datbases maintained by the application provider.

App SDK allows Apps:

- send and receive messages to/from the Gateway,
- request intent analysis (API.ai), and
- provide local testings

## To Design

Followings are features haven't be designed:

- Enables the application to send messages to clients asychronizedly
