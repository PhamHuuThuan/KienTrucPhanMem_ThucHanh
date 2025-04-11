from flask import Flask, request, render_template_string
import redis
import os

app = Flask(__name__)
r = redis.Redis(host='redis', port=6379)

template = """
<form action="/" method="POST">
    <input type="submit" name="vote" value="Cats">
    <input type="submit" name="vote" value="Dogs">
</form>
"""

@app.route("/", methods=["GET", "POST"])
def index():
    if request.method == "POST":
        vote = request.form["vote"]
        r.rpush("votes", vote)
    return render_template_string(template)

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=80)
