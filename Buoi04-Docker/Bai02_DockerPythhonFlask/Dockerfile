#Tai moi truong python
FROM python:3.9

#Thiet lap thu muc container
WORKDIR /app

#sao chep file dependencies
COPY requirements.txt ./

#Cai dat dependency
RUN pip install --no-cache-dir -r requirements.txt

#Sao chep code
COPY app.py ./

#Expose cong 5000
EXPOSE 5000

#Chay ung dung
CMD ["python", "app.py"]