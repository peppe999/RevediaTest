Chart.defaults.global.defaultFontFamily = 'Poppins';
var ctx = document.getElementById('myChart');
var myChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: ['01/01', '02/01', '03/01', '04/01', '05/01', '06/01', '07/01', '08/01', '09/01', '10/01', '11/01', '12/01'],
        datasets: [{
            label: 'Punteggio',
            data: [0, 19, 3, 5, 1, 3, 0, 19, 3, 5, 1, 3],
            backgroundColor: "rgba(230,105,71,0.1)",
            borderColor: "#e66947",
            borderWidth: 2
        }]
    },
    options: {
        maintainAspectRatio: false,
        layout: {
            padding: {
                left: 25,
                right: 25,
                top: 10,
                bottom: 0
            }
        },
        legend: {
            display: false
        },
        scales: {
            yAxes: [{
                gridLines: {
                    color: "rgba(230,105,71,0.1)",
                    zeroLineColor: "rgba(230,105,71,0.1)",
                    drawBorder: false,
                    drawTicks: false,
                    borderDash: ["0"],
                    zeroLineBorderDash: ["0"],
                    drawOnChartArea: false
                },
                ticks: {
                    display: false,
                    fontColor: "#696969",
                    beginAtZero: true,
                    padding: 0
                }
            }], 
            xAxes: [{
                gridLines: {
                    color: "rgba(230,105,71,0.1)",
                    zeroLineColor: "rgba(230,105,71,0.1)",
                    drawBorder: false,
                    drawTicks: false,
                    borderDash: ["0"],
                    zeroLineBorderDash: ["0"],
                    drawOnChartArea: true
                },
                ticks: {
                    display: true,
                    fontColor: "#696969",
                    beginAtZero: true,
                    padding: 10,
                    maxRotation: 0,
                    autoSkipPadding: 12
                }
            }]
        },
        tooltips: {
            /*backgroundColor: "#f0f0f0",
            borderWidth: 1,
            borderColor: "#cacaca",
            cornerRadius: 10,
            titleFontSize: 12,
            titleFontColor: "#a0a0a0"*/
            enabled: false,
            custom: function(tooltipModel) {
                // Tooltip Element
                var tooltipEl = document.getElementById('chartjs-tooltip');

                // Create element on first render
                if (!tooltipEl) {
                    tooltipEl = document.createElement('div');
                    tooltipEl.id = 'chartjs-tooltip';
                    tooltipEl.innerHTML = '<table></table>';
                    document.body.appendChild(tooltipEl);
                }

                // Hide if no tooltip
                if (tooltipModel.opacity === 0) {
                    tooltipEl.style.opacity = 0;
                    tooltipEl.style.display = "none";
                    return;
                }

                // Set caret Position
                tooltipEl.classList.remove('above', 'below', 'no-transform');
                if (tooltipModel.yAlign) {
                    tooltipEl.classList.add(tooltipModel.yAlign);
                } else {
                    tooltipEl.classList.add('no-transform');
                }

                function getBody(bodyItem) {
                    return bodyItem.lines;
                }

                // Set Text
                if (tooltipModel.body) {
                    var titleLines = tooltipModel.title || [];
                    var bodyLines = tooltipModel.body.map(getBody);

                    var innerHtml = '<thead>';

                    titleLines.forEach(function(title) {
                        innerHtml += '<tr><th class="chartjs-tooltip-heading">' + title + '</th></tr>';
                    });
                    innerHtml += '</thead><tbody>';

                    bodyLines.forEach(function(body, i) {
                        var colors = tooltipModel.labelColors[i];
                        var style = 'background:' + colors.backgroundColor;
                        style += '; border-color:' + colors.borderColor;
                        style += '; border-width: 2px';
                        var span = '<span style="' + style + '"></span>';
                        innerHtml += '<tr class="chartjs-tooltip-value"><td>' + span + body + '</td></tr>';
                    });
                    innerHtml += '</tbody>';

                    var tableRoot = tooltipEl.querySelector('table');
                    tableRoot.innerHTML = innerHtml;
                }

                // `this` will be the overall tooltip
                var position = this._chart.canvas.getBoundingClientRect();

                // Display, position, and set styles for font
                tooltipEl.style.display = "block";
                tooltipEl.style.opacity = 1;

                var leftPosition = position.left + window.pageXOffset + tooltipModel.caretX;
                if(leftPosition > position.right - tooltipEl.clientWidth)
                    leftPosition =  leftPosition - tooltipEl.clientWidth - 2;
                tooltipEl.style.left = leftPosition + 'px';

                var topPosition = position.top + window.pageYOffset + tooltipModel.caretY;
                if(topPosition > position.bottom - tooltipEl.clientHeight)
                    topPosition =  topPosition - tooltipEl.clientHeight - 5;
                tooltipEl.style.top = topPosition + 'px';
            }
        }
    }
});