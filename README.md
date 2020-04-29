# QualiChain Analytics Intelligent Profiling

## How to install
    - Clone this repository (git clone https://github.com/QualiChain/analytics.git)

## Configuration
    - Set the address of Sparql Endpoint in src/IP/config/app.config file 

## Create Docker image

Run docker build -t profiling .

## Runnig Docker container

Run docker run --publish 8000:8000 -t profiling
