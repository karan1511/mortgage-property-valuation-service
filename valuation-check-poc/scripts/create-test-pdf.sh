#!/bin/bash

# Script to create a sample PDF for testing

set -e

echo "📄 Creating sample PDF for testing..."

# Create a sample PDF using a simple HTML to PDF conversion
# This creates a basic PDF with sample valuation data

cat > sample-valuation.html << 'EOF'
<!DOCTYPE html>
<html>
<head>
    <title>Property Valuation Report</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .header { text-align: center; border-bottom: 2px solid #333; padding-bottom: 20px; }
        .section { margin: 20px 0; }
        .property-details { background-color: #f5f5f5; padding: 15px; }
        .valuation-summary { background-color: #e8f4fd; padding: 15px; }
        .risk-assessment { background-color: #fff3cd; padding: 15px; }
    </style>
</head>
<body>
    <div class="header">
        <h1>Property Valuation Report</h1>
        <p>Report ID: rt74321 | Date: 15th January 2024</p>
    </div>

    <div class="section property-details">
        <h2>Property Details</h2>
        <p><strong>Address:</strong> 123 Main Street, Apartment 4B, London, SW1A 1AA</p>
        <p><strong>Property Type:</strong> Apartment</p>
        <p><strong>Bedrooms:</strong> 2</p>
        <p><strong>Bathrooms:</strong> 2</p>
        <p><strong>Reception Rooms:</strong> 1</p>
        <p><strong>Total Floor Area:</strong> 85.5 sq m</p>
        <p><strong>Year Built:</strong> 2015</p>
        <p><strong>Condition:</strong> Good</p>
    </div>

    <div class="section valuation-summary">
        <h2>Valuation Summary</h2>
        <p><strong>Estimated Value:</strong> £750,000</p>
        <p><strong>Valuation Range:</strong> £700,000 - £800,000</p>
        <p><strong>Valuation Method:</strong> Comparable Sales</p>
        <p><strong>Valuation Date:</strong> 15th January 2024</p>
        <p><strong>Price per sq m:</strong> £8,772</p>
    </div>

    <div class="section">
        <h2>Market Analysis</h2>
        <p><strong>Local Market Trend:</strong> Stable with slight upward movement</p>
        <p><strong>Average Price per sq m in area:</strong> £8,500</p>
        <p><strong>Comparable Properties:</strong></p>
        <ul>
            <li>125 Main Street - £720,000 (Dec 2023) - 0.2 miles</li>
            <li>127 Main Street - £780,000 (Nov 2023) - 0.1 miles</li>
            <li>121 Main Street - £740,000 (Oct 2023) - 0.3 miles</li>
        </ul>
    </div>

    <div class="section risk-assessment">
        <h2>Risk Assessment</h2>
        <p><strong>Overall Risk Level:</strong> Low</p>
        <p><strong>Risk Score:</strong> 2/10</p>
        <p><strong>Risk Factors:</strong> None identified</p>
        <p><strong>Recommendations:</strong></p>
        <ul>
            <li>Property is suitable for mortgage lending</li>
            <li>Consider standard lending terms</li>
            <li>Property is in good condition with modern amenities</li>
        </ul>
    </div>

    <div class="section">
        <h2>Additional Notes</h2>
        <p>The property is located in a desirable area of London with excellent transport links. The building is well-maintained and the apartment has been recently refurbished. The valuation is based on recent comparable sales in the immediate area and reflects current market conditions.</p>
    </div>
</body>
</html>
EOF

# Check if wkhtmltopdf is available
if command -v wkhtmltopdf &> /dev/null; then
    echo "📄 Converting HTML to PDF using wkhtmltopdf..."
    wkhtmltopdf sample-valuation.html sample-valuation.pdf
    echo "✅ Sample PDF created: sample-valuation.pdf"
elif command -v pandoc &> /dev/null; then
    echo "📄 Converting HTML to PDF using pandoc..."
    pandoc sample-valuation.html -o sample-valuation.pdf
    echo "✅ Sample PDF created: sample-valuation.pdf"
else
    echo "⚠️  Neither wkhtmltopdf nor pandoc is available."
    echo "   Please install one of them to create a PDF, or use the HTML file directly."
    echo "   HTML file created: sample-valuation.html"
fi

# Clean up HTML file
rm sample-valuation.html

echo "🎉 Sample PDF creation complete!"
echo "   You can now upload this PDF to your S3 bucket for testing."
