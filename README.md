# analytics
1. Set the address of Sparql Endpoint of the knowledge engine in config/app.config file 

2. Create a Docker image using the fillowing command:
docker build -t profiling .


3. Run Docker container:
docker run --publish 8000:8000 -t profiling
