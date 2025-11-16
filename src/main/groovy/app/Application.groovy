package app


static void main(String[] args) {
    AppContainer container = AppContainer.getInstancia()
    try {
        Linketinder linketinder = container.criarAplicacao()
        linketinder.mostrarMenu()
    } catch (Exception e) {
        e.printStackTrace()
    }
    finally {
        container.fecharConexao()
    }
}