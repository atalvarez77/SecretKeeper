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

## 🔮 Future Improvements
* Upgrade the Cipher mode from `ECB` to `GCM` (Galois/Counter Mode) for authenticated encryption.
* Implement "Salting" for the password hash.