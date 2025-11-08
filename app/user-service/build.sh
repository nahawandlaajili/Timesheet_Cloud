# #!/bin/bash

# echo "=== Building User Service ==="
# # Clean previous builds
# mvn clean

# # Run tests with coverage
# echo "=== Running Tests ==="
# mvn test

# # Check for vulnerabilities
# echo "=== Security Scan ==="
# mvn dependency-check:check

# # Build package
# echo "=== Building Package ==="
# mvn package -DskipTests

# # Generate reports
# echo "=== Generating Reports ==="
# mvn jacoco:report site:site

# echo "=== Build Complete ==="
#!/bin/bash

echo "=== Building User Service ==="

# Temporarily disable Nexus settings
export MAVEN_OPTS="-Dmaven.repo.local=/tmp/m2repo-$$"

# Clean previous builds
echo "=== Cleaning ==="
mvn clean -s /dev/null

# Run tests with coverage
echo "=== Running Tests ==="
mvn test -s /dev/null

# Check for vulnerabilities
echo "=== Security Scan ==="
mvn dependency-check:check -s /dev/null

# Build package
echo "=== Building Package ==="
mvn package -DskipTests -s /dev/null

# Generate reports
echo "=== Generating Reports ==="
mvn jacoco:report site:site -s /dev/null

echo "=== Build Complete ==="