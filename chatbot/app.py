from flask import Flask, request, jsonify
import rasa
from rasa.core.agent import Agent
import asyncio

app = Flask(__name__)

# Load model
model_path = "./models/nlu-20230611-091104.tar.gz"
agent = Agent.load(model_path)

# Define API endpoint
@app.route('/chatbot', methods=['POST'])
async def chatbot():
        # Get input message from request
    message = request.json['message']

    # Use Rasa agent to handle input message
    response = await agent.handle_text(message)

    if response:
        # Extract the first response from Rasa agent
        first_response = response[0]

        # Extract the response text
        response_text = first_response['text']

        return response_text
    else:
        return "No response from the chatbot"

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
