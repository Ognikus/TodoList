function loadLists(groupId) {
    if (!groupId) {
        console.error('Group ID is undefined');
        return;
    }

    fetch(`/groups/${groupId}/lists`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.text();
        })
        .then(html => {
            document.getElementById('main-content').innerHTML = html;
        })
        .catch(error => {
            console.error('Error loading lists:', error);
            alert('Произошла ошибка при загрузке списков. Попробуйте ещё раз.');
        });
}

function showCreateGroupForm() {
    document.getElementById('main-content').innerHTML = `
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Создать группу</h5>
                        <form action="/groups" method="post">
                            <div class="mb-3">
                                <label for="groupName" class="form-label">Название группы</label>
                                <input type="text" class="form-control" id="groupName" name="name" required>
                            </div>
                            <button type="submit" class="btn btn-primary">Сохранить</button>
                            <button type="button" class="btn btn-secondary" onclick="cancelCreateGroup()">Отмена</button>
                        </form>
                    </div>
                </div>
            `;
}

function cancelCreateGroup() {
    document.getElementById('main-content').innerHTML = `
                <h1 class="text-center">Добро пожаловать в приложение ToDo List</h1>
                <p class="text-muted text-center">Выберите группу или создайте новую, чтобы начать работу.</p>
            `;
}


function loadTasks(listId) {
    if (!listId) {
        console.error('List ID is undefined');
        return;
    }

    fetch(`/lists/${listId}/tasks`) // Делаем запрос к серверу
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.text();
        })
        .then(html => {
            document.getElementById('main-content').innerHTML = html; // Загружаем HTML в main-content
        })
        .catch(error => {
            console.error('Error loading tasks:', error);
            alert('Произошла ошибка при загрузке задач. Попробуйте ещё раз.');
        });
}

function deleteTask(taskId, listId) {
    if (!taskId || !listId) {
        console.error('Task ID or List ID is undefined');
        return;
    }

    fetch(`/tasks/${taskId}/delete`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.text();
        })
        .then(html => {
            // Обновляем содержимое main-content
            document.getElementById('main-content').innerHTML = html;
        })
        .catch(error => {
            console.error('Error deleting task:', error);
            alert('Произошла ошибка при удалении задачи. Попробуйте ещё раз.');
        });
}


function toggleTask(taskId, listId) {
    if (!taskId || !listId) {
        console.error('Task ID or List ID is undefined');
        return;
    }

    fetch(`/tasks/${taskId}/toggle`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.text();
        })
        .then(html => {
            // Обновляем содержимое main-content
            document.getElementById('main-content').innerHTML = html;
        })
        .catch(error => {
            console.error('Error toggling task:', error);
            alert('Произошла ошибка при переключении статуса задачи. Попробуйте ещё раз.');
        });
}

function createTask(listId) {
    const input = document.querySelector(`#taskInput-${listId}`);
    if (!input || !input.value.trim()) {
        alert('Введите название задачи.');
        return;
    }

    const formData = new FormData();
    formData.append('title', input.value.trim()); // Название задачи
    formData.append('listId', listId);           // ID списка

    fetch('/tasks', {
        method: 'POST',
        body: formData,
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.text();
        })
        .then(html => {
            // Обновляем содержимое main-content с новыми задачами
            document.getElementById('main-content').innerHTML = html;
            input.value = ''; // Очищаем поле ввода
        })
        .catch(error => {
            console.error('Error creating task:', error);
            alert('Произошла ошибка при добавлении задачи. Попробуйте ещё раз.');
        });
}







