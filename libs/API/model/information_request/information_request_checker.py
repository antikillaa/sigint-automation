

class InformationRequestChecker:

    def __init__(self):
        pass

    @staticmethod
    def check(query, response):
        assert query.createdDate == response.createdDate
        assert query.description == response.description
        assert query.distributionList == response.distributionList
        assert query.externalRequestNumber == response.externalRequestNumber
        assert query.goals == response.goals
        assert query.priority == response.priority
        assert query.requestSource == response.requestSource
        assert query.searchType == response.searchType
        assert query.state == response.state
        assert query.subject == response.subject
        assert query.taskCategories == response.taskCategories
        assert query.dueDate == response.dueDate
        assert response.id
        assert response.internalRequestNumber
