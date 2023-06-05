# Metroid Password Generator

Read and generate passwords based on Metroid system.

# Execution

## Requirements

- [Docker][docker] (tested on version 23.0.6)

The fastest way to run this project is to generate its Docker image. Under the project's root directory, execute `docker build -t marceloleite2604/mpg .` to build it. Once the image is available, run `docker run --rm -v ./input:/opt/project/input:ro marceloleite2604/mpg -e -i ./input/example.json` to generate a password based on [example.json][example-json] file.

## Build

## Requirements

- [Maven][maven] (tested on version 3.8.7)
- Java Development Kit (JDK): Minimal required version is 19. I recommend use [Eclipse Temurin][eclipse-temurin].

To build the project, run `mvn clean package` on the project root directory. This will generate a file named `metroid-password-generator-1.0-SNAPSHOT-jar-with-dependencies.jar` under the `target` directory. To execute it, run `java -jar target/metroid-password-generator-1.0-SNAPSHOT-jar-with-dependencies.jar -e -i ./input/example.json` to generate a password based on [example.json][example-json] file.

# Parameters

The Metroid Password Generator program accepts two sets of parameters: Encoding and decoding.

## Encoding Set

Use this set to generate a new password based on a JSON file. This set can be activated with `--encode` (or its abbreviation `-e`) and it requires an extra parameter `--input-file` (abbreviated as `-i`) which presents the JSON file location.

**Examples:**
```bash
java -jar target/metroid-password-generator-1.0-SNAPSHOT-jar-with-dependencies.jar --encode --input-file ./input/tourian-without-varia-suit.json
```

```bash
java -jar target/metroid-password-generator-1.0-SNAPSHOT-jar-with-dependencies.jar -e -i ./input/brinstar-all-missile-containers.json
```

## Decoding Set

Use this set to decode a password and check which information it stores (acquisitions, energy tanks, doors, kills, items, starting point, etc). This set can be activated with `--decode` (or its abbreviation `-d`) and it requires an extra parameter `--password` (abbreviated as `-p`) which informs the password to be decoded.

**Examples:**

```bash
java -jar target/metroid-password-generator-1.0-SNAPSHOT-jar-with-dependencies.jar --decode --password "-----u F1t--v ----?7 ---scA"
```

```bash
java -jar target/metroid-password-generator-1.0-SNAPSHOT-jar-with-dependencies.jar -d -p "-----u F1t--v ----?7 ---scA"
```

# Encoding Input File

The encoding input file is a JSON file that contains all information that must be on the password. New files can be created based on [example.json][example-json] file located at `input` directory.

# Credits and Acknowledgement
This project is based on [John David Ratliff's Metroid Password Format Guide][metroid-password-format-guide]. This project would not be possible without his well-written document and awesome [MPG project][jdr-mpg-project].

# Donation

If you liked the project and *really* want to demonstrate your appreciation, you can send me a "thank you" coffee. 🙂

[![Yellow PayPal Donation button with "donate" text written on it](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)][paypal-donation]


[docker]: https://docs.docker.com/get-docker/
[eclipse-temurin]: https://adoptium.net/temurin/releases/
[example-json]: ./input/example.json
[jdr-mpg-project]: http://games.technoplaza.net/mpg/
[maven]: https://maven.apache.org/download.cgi
[metroid-password-format-guide]: https://games.technoplaza.net/mpg/password.txt
[paypal-donation]: https://www.paypal.com/donate/?hosted_button_id=C6LPXWCHGRUVQ