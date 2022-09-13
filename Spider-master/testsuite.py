import subprocess as sp
import requests
import signal
import yaml
import socket
from io import BytesIO
from http.client import HTTPResponse
from termcolor import colored
import time


class FakeSocket:

    def __init__(self, data):
        self.content = BytesIO(data)

    def makefile(self, *argsn, **kwargs):
        return self.content


class Netcat:

    def __init__(self, ip, port):
        self.ip = ip
        self.port = port
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    def connect(self):
        self.socket.connect((self.ip, self.port))

    def send(self, data):
        self.socket.send(data)

    def recv(self):
        return self.socket.recv(1024)


test_file = "test.yaml"

# Open Test File
with open(test_file, "r") as f:
    tests = yaml.safe_load(f)

# Launch Spider
spider_process = sp.Popen(["./spider","tests/config_file2.json"],
        stdout=sp.PIPE, stderr=sp.PIPE, bufsize=0)

# Test if Spider is Ready
with open(f"/proc/{spider_process.pid}/cmdline", "r") as process_cmdline:
    if "[READY]" in process_cmdline.readline():
        print("Spider is open, we can start requests")

#netcat
data = b"GET /tests HTTP-0w0\r\nHost: localhost\r\n\r\n"
netcat = Netcat("127.0.0.1", 8000)
netcat.connect()
netcat.send(data)

response = netcat.recv()

#Fake socket
response = HTTPResponse(FakeSocket(response))
response.begin()

print(response.status)
print(response.getheaders())
print(response.read())


def check_status_code(response, expected):
    assert response == expected, "Invalid status code..."

for test in tests['tests']:
    #send requests
    response = requests.get(test['url'])

    try:
        check_status_code(response.status_code, test.get("status_code", 200))
        print(colored(f"[OK] {test['name']}", "blue"))

    except AssertionError as err:
        print(err)
        print(colored(f"[KO] {test['name']}", "red"))


# Send SIGINT
spider_process.send_signal(signal.SIGINT)
