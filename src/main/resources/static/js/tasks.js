
var checkBoxes, boxesChekeds;

$(document).ready(function () {

    $('#check').on('change','.completeTask', completeTask);

    function completeTask() {
        alert("entrou aqui");
        $(this).parent().toggleClass('done');
        updateProgressTasks();
    }

    function updateProgressTasks() {
        countCheckBox();
        var percent = String(calculatePercent()) + '%';
        $(".progress-bar").width(percent);
        $("#textPercent").text('Progress: ' + percent);


    }

    function countCheckBox() {
        var inputs, i;

        checkBoxes = 0;
        boxesChekeds = 0;
        inputs = document.getElementsByTagName('checkBox');

        for (i = 0; i < inputs.length; i++) {
            if (inputs[i].type == 'checkbox' && inputs[i].id == 'check') {
                checkBoxes++;
                if (inputs[i].checked == true) {
                    boxesChekeds++;
                }
            }
        }
    }

    function calculatePercent() {
        if(checkBoxes == 0){
            return 0;
        }
        var percent = (100 * boxesChekeds)/checkBoxes;
        return parseInt(percent);
    }
});
