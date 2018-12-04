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
                "asc"
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
        datatableApi.clear().rows.add(data).draw();
    });
}

function reset() {
    $("#filterForm").find(":input").val("");
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
