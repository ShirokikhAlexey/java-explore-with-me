FROM amazoncorretto:11

COPY . /app/.
RUN apt-get update && apt-get install dos2unix && dos2unix /app/entrypoint.sh && chmod +x /app/entrypoint.sh

WORKDIR /app

ENTRYPOINT ["/app/entrypoint.sh"]
CMD ["bash"]