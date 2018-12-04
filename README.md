# Peaceword
Simple local password manager to help people that can only remember one password to have secure and different for all websites.

Chrome extension here: 
Apk here : 

Load a chrome extension in unpack mode here: https://stackoverflow.com/questions/24577024/install-chrome-extension-not-in-the-store

## Screenshots
### Chrome extension
![Mainscreen.jpg](https://raw.githubusercontent.com/oom-/peaceword/master/screenshots/Mainscreen.JPG)
![Configscreen.jpg](https://raw.githubusercontent.com/oom-/peaceword/master/screenshots/Configscreen.JPG)
### Android app

## How it work ?

Really simple : 
1. Take the domain name
2. Apply sha256 with your own custom salt (generated on first run) to the domain
3. Apply changes from the settings (length, needupercase and needspecialchar)
4. Display the password

To secure your password event more you can use the password from the extension and add your common password at the end.
By exemple if Peaceword generate "006631D?" you can use at password "006631D?MyEasyPasswordToRemember1".

## What you can do with it ?

* You can secure your password and make them hard to guess to hackers even if you are using the "same password" for all website (obviously if you put the one generated by Peaceword front or at the end of it).
* You can use it for other stuff than internet with the manual input.
* You can export your salt on another application with the QRcode system (gen and scan from android).
* You can fill automatically all password fileds on the current html page.
* You can copy in clipboard the current password (necessary when you want a password of 7000 characters).
* The length of the password is the limit of integer signed type (2,147,483,647 characters);
* You can import salt manually (put what you want).
* You can use it from your phone (android only for now).

