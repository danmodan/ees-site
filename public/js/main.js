$(function () {
    $('[data-toggle="tooltip"]').tooltip()
})
// phone mask
$(function () {
    var maskBehavior = function (val) {
        return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
    },
        options = {
            onKeyPress: function (val, e, field, options) {
                field.mask(maskBehavior.apply({}, arguments), options);
            }
        };

    $('.phone').mask(maskBehavior, options);
})
// cep mask
$(function () {
    var maskBehavior = function (val) {
        return '00000-000';
    },
        options = {
            onKeyPress: function (val, e, field, options) {
                field.mask(maskBehavior.apply({}, arguments), options);
            }
        };

    $('.cep').mask(maskBehavior, options);
})
// cep call
$(function () {
    $("#cep").focusout(function () {
        $.ajax({
            url: 'https://viacep.com.br/ws/' + $(this).val().replace(/\D/g, '') + '/json/unicode/',
            dataType: 'json',
            success: function (resposta) {
                var formLogradouro = $("#logradouro");
                formLogradouro.val(resposta.logradouro);

                var formComplemento = $("#complemento");
                formComplemento.val(resposta.complemento);

                var formBairro = $("#bairro");
                formBairro.val(resposta.bairro);

                var formCidade = $("#cidade");
                formCidade.val(resposta.localidade);

                var formUf = $("#uf");
                formUf.val(resposta.uf);

                formLogradouro.removeAttr("disabled");
                formComplemento.removeAttr("disabled");
                formBairro.removeAttr("disabled");
                formCidade.removeAttr("disabled");
                formUf.removeAttr("disabled");

                formNumero = $("#numero");
                formNumero.removeAttr("disabled");
                $("#numero").focus();
            }
        });
    });
})
$(function () {
    bsCustomFileInput.init()
})
$(function () {
    $("#form-alert-message").hide();
})
// send form
$(function () {
    $('#contact-form').bind("submit", function (e) {

        e.preventDefault();

        var form = $(this);
        var url = form.attr('action');
        var method = form.attr('method');

        var formData = new FormData();

        formData.append('nome', $('#nome').val());
        formData.append('email', $('#email').val());
        formData.append('mensagem', $('#mensagem').val());
        formData.append('telefone', $('#telefone').val());
        formData.append('whatsapp', $('#whatsapp').is(":checked"));
        formData.append('cep', $('#cep').val());
        formData.append('logradouro', $('#logradouro').val());
        formData.append('numero', $('#numero').val());
        formData.append('bairro', $('#bairro').val());
        formData.append('cidade', $('#cidade').val());
        formData.append('uf', $('#uf').val());
        formData.append('complemento', $('#complemento').val());

        $.each($("#contaLuz")[0].files, function(i, file) {
            formData.append('contaLuz', file);
        });

        var alertMessage = $("#form-alert-message");

        $.ajax({
            url: url,
            type: method,
            data: formData,
            contentType: false,
            processData: false,
            success: function(result){
                alertMessage.text("Obrigada! Entraremos em contato.")
                alertMessage.removeClass("alert-danger");
                alertMessage.addClass("alert-success");
                alertMessage.show();
                alertMessage.delay(10000).fadeOut();
            },
            error: function(err){
                alertMessage.text("Falhou! Por favor, entre em contato por nossos telefones.")
                alertMessage.removeClass("alert-success");
                alertMessage.addClass("alert-danger");
                alertMessage.show();
                alertMessage.delay(10000).fadeOut();
            }
        });
    });
})