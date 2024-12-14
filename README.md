# Sky Search AI

Sky Search AI is a learning-focused Android application designed for seamless interaction with AI. This project was developed to enhance understanding of mobile app development, API integration, and AI capabilities, including natural language processing and image generation.

## Overview
Sky Search AI is an easy-to-use AI-powered application that allows users to ask questions and receive responses from AI in both text and image formats. It features a clean and simple user interface for an optimized user experience.

## Features
### User Authentication
- **Registration Page:** Allows new users to register with an email ID and password.
- **Login Page:** Existing users can log in securely using their credentials.
- **Logout Functionality:** Users can log out of their accounts with ease.

### AI Chat Interface
- Users can chat with AI, receiving natural language responses.
- Includes options for generating images using AI APIs.

### UI/UX Design
- Simplified and user-friendly interface.
- Integrated Firebase for authentication.
- Enhanced chat experience with the Volley library for smoother interactions.

### External APIs Used
- **Firebase Authentication:** For email and password authentication.
- **OpenAI API:** For ChatGPT-based text responses (`https://api.openai.com/v1/chat/completions`).
- **DALL·E API:** For AI-generated images (`https://api.openai.com/v1/images/generations`).
- **Picasso Library:** For displaying images within the app.
- **Volley Library:** For handling HTTP requests and enhancing app performance.

## App Flow
1. **Splash Screen**: Initial loading screen.
2. **Login/Register Screens**: User authentication pages.
3. **Main Screen**: Chat interface with options to enable text-to-speech and generate images.
4. **Feedback Screen**: Users can provide feedback for the app.

## Challenges Faced
- Resolving compatibility issues while installing the Volley library.
- Understanding and implementing Firebase integration with the help of official documentation.
- Debugging API requests to ensure seamless functionality.

## Future Modifications
- Adding a "Forgot Password" functionality.
- Redesigning the UI for better usability and aesthetics.
- Implementing a text-to-speech feature for enhanced accessibility.

## How to Test
1. Clone the repository from GitHub: [Sky Search AI Repository](https://github.com/abodhale/SkySearchAI).
2. Register as a new user or use the following test credentials:
   - **Email:** Akash@yahoo.com  
   - **Password:** Akash3011@
3. Note: The OpenAI API key used for testing in the repository may be invalid. Replace it with your own key to test the application fully.

## Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/abodhale/SkySearchAI.git
    ```
2. Open the project in Android Studio.
3. Build and run the application on an emulator or a physical device.

## Images of working Model
Text response

<img width="459" alt="Screenshot 2024-12-14 at 2 02 07 AM" src="https://github.com/user-attachments/assets/a9304eb4-ca67-4241-bbad-eac687882027" />

Image Response

<img width="449" alt="Screenshot 2024-12-14 at 2 02 40 AM" src="https://github.com/user-attachments/assets/6768f8d1-a2e5-450c-9128-3a72851b80cf" />



## License
This project was developed for learning purposes and is not intended for commercial use. Feel free to explore, modify, and learn from it.

---

For any queries or feedback, reach out at **abodhale@hawk.iit.edu**.
