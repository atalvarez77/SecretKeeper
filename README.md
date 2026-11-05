# Secret Keeper 🔒

A local, desktop-based secure vault for encrypting and decrypting sensitive text using AES-256 cryptography. Built entirely in Java as part of a bi-weekly "Vibe Coding" streak focusing on Software Engineering and Cybersecurity fundamentals.

## 🛠 Tech Stack & Libraries
* **Language:** Java 17
* **UI Framework:** JavaFX (via Azul Zulu FX build)
* **Cryptography:** `javax.crypto` (AES-256, SHA-256 Hashing)
* **File I/O:** `java.nio.file` (Modern Java NIO)

## 🧠 How the Cryptography Works (The Engine)
This application does not rely on third-party security libraries; it utilizes the built-in Java Cryptography Architecture (JCA).
1.  **Key Generation:** User passwords are not used directly. They are hashed using `SHA-256` via `MessageDigest` to guarantee a cryptographically secure, 256-bit (32-byte) symmetric key.
2.  **Encryption:** The application utilizes the Advanced Encryption Standard (AES). The cipher is initialized with `AES/ECB/PKCS5Padding`.
3.  **Encoding:** The resulting raw encrypted bytes are encoded using `Base64` to ensure the data can be safely displayed in the UI and written to standard `.txt` files without data corruption.

## 🏗 Software Architecture
The application follows the **Separation of Concerns** principle:
* `SecretKeeper.java`: The Frontend Controller. Manages the JavaFX stage, UI state (clearing sensitive data), and event listeners.
* `CryptoUtils.java`: The Security Engine. Handles all hashing, byte manipulation, and Cipher initialization.
* `FileManager.java`: The Storage Utility. Handles reading and writing strings to the local filesystem using native macOS file dialogs.

## 🚀 How to Run (Local Machine)
1. Ensure you have a Java 17+ JDK installed that includes JavaFX (e.g., `zulufx` via Homebrew on macOS).
2. Open the project folder in your IDE or terminal.
3. Compile and execute `SecretKeeper.java`. ('javac SecretKeeper.java' then 'java SecretKeeper')
4. The application runs entirely offline. No data is sent to external servers.

## Execution
Opening Page:
<img width="492" height="447" alt="image" src="https://github.com/user-attachments/assets/a0331588-2af8-47b4-97aa-de4fbc224df0" />
"Hello, this is my secret file!" with password "12345:
<img width="498" height="443" alt="image" src="https://github.com/user-attachments/assets/d4805caa-ea45-4833-8f5e-8567ba33f97b" />
Encrypted Text w/ save to file option:
<img width="498" height="431" alt="image" src="https://github.com/user-attachments/assets/0beb8d60-9899-4f5b-93be-ca4b47dcaff7" />
Text before and after decryption using password:
<img width="499" height="413" alt="image" src="https://github.com/user-attachments/assets/16e13d2c-7f76-4ffc-9253-c4fc44f10d35" />
<img width="502" height="441" alt="image" src="https://github.com/user-attachments/assets/59c3ae46-93f8-449f-8436-22d3374f4c0f" />







## 🔮 Future Improvements
* Upgrade the Cipher mode from `ECB` to `GCM` (Galois/Counter Mode) for authenticated encryption.
* Implement "Salting" for the password hash.
