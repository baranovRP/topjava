const ajaxUrl = "ajax/meals/";
let datatableApi;

$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ]
    });
    makeEditable();
});

function filter() {
    let form = $("#filterForm");
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: form.serialize()
    }).done(function (data) {
        clearAndDrawRows(data)
    });
}

function reset() {
    $("#filterForm").trigger("reset");
    updateTable();
}

function saveMeal() {
    let form = $("#detailsForm");
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        filter();
        successNoty("Saved");
    });
}
